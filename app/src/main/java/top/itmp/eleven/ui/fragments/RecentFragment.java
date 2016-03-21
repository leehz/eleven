/*
 * Copyright (C) 2012 Andrew Neal
 * Copyright (C) 2014 The CyanogenMod Project
 * Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package top.itmp.eleven.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;

import top.itmp.eleven.Config;
import top.itmp.eleven.Config.SmartPlaylistType;
import top.itmp.eleven.R;
import top.itmp.eleven.adapters.SongAdapter;
import top.itmp.eleven.loaders.TopTracksLoader;
import top.itmp.eleven.menu.FragmentMenuItems;
import top.itmp.eleven.model.Song;
import top.itmp.eleven.sectionadapter.SectionCreator;
import top.itmp.eleven.sectionadapter.SectionListContainer;
import top.itmp.eleven.ui.activities.BaseActivity;
import top.itmp.eleven.ui.fragments.profile.SmartPlaylistFragment;
import top.itmp.eleven.utils.MusicUtils;
import top.itmp.eleven.widgets.NoResultsContainer;

import java.util.TreeSet;

/**
 * This class is used to display all of the recently listened to songs by the
 * user.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class RecentFragment extends SmartPlaylistFragment implements ISetupActionBar {

    @Override
    protected SmartPlaylistType getSmartPlaylistType() {
        return Config.SmartPlaylistType.RecentlyPlayed;
    }

    @Override
    protected void updateMenuIds(TreeSet<Integer> set) {
        set.add(FragmentMenuItems.REMOVE_FROM_RECENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Loader<SectionListContainer<Song>> onCreateLoader(final int id, final Bundle args) {
        // show the loading progress bar
        mLoadingEmptyContainer.showLoading();

        TopTracksLoader loader = new TopTracksLoader(getActivity(),
                TopTracksLoader.QueryType.RecentSongs);
        return new SectionCreator<Song>(getActivity(), loader, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMetaChanged() {
        super.onMetaChanged();

        // refresh the list since a track playing means it should be recently played
        restartLoader();
    }

    @Override
    public void setupNoResultsContainer(NoResultsContainer empty) {
        super.setupNoResultsContainer(empty);

        empty.setMainText(R.string.empty_recent_main);
        empty.setSecondaryText(R.string.empty_recent);
    }

    @Override
    public void setupActionBar() {
        ((BaseActivity)getActivity()).setupActionBar(R.string.playlist_recently_played);
    }

    @Override
    protected long getFragmentSourceId() {
        return Config.SmartPlaylistType.RecentlyPlayed.mId;
    }

    @Override
    protected SongAdapter createAdapter() {
        return new RecentAdapter(
            getActivity(),
            R.layout.list_item_normal,
            getFragmentSourceId(),
            getFragmentSourceType()
        );
    }

    private class RecentAdapter extends SongAdapter {
        public RecentAdapter(Activity context, int layoutId, long sourceId, Config.IdType sourceType) {
            super(context, layoutId, sourceId, sourceType);
        }

        @Override
        protected boolean showNowPlayingIndicator(Song song, int position) {
            return position == 0 && super.showNowPlayingIndicator(song, position);
        }
    }

    @Override
    protected int getShuffleTitleId() { return R.string.menu_shuffle_recent; }

    @Override
    protected int getClearTitleId() { return R.string.clear_recent_title; }

    @Override
    protected void clearList() { MusicUtils.clearRecent(getActivity()); }
}