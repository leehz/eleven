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

package top.itmp.eleven.ui.fragments.profile;

import android.os.Bundle;
import android.support.v4.content.Loader;

import top.itmp.eleven.Config;
import top.itmp.eleven.Config.SmartPlaylistType;
import top.itmp.eleven.R;
import top.itmp.eleven.loaders.LastAddedLoader;
import top.itmp.eleven.model.Song;
import top.itmp.eleven.sectionadapter.SectionCreator;
import top.itmp.eleven.sectionadapter.SectionListContainer;
import top.itmp.eleven.ui.activities.BaseActivity;
import top.itmp.eleven.ui.fragments.ISetupActionBar;
import top.itmp.eleven.utils.MusicUtils;
import top.itmp.eleven.widgets.NoResultsContainer;

/**
 * This class is used to display all of the songs the user put on their device
 * within the last four weeks.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class LastAddedFragment extends SmartPlaylistFragment implements ISetupActionBar {

    /**
     * {@inheritDoc}
     */
    @Override
    public Loader<SectionListContainer<Song>> onCreateLoader(final int id, final Bundle args) {
        // show the loading progress bar
        mLoadingEmptyContainer.showLoading();

        LastAddedLoader loader = new LastAddedLoader(getActivity());
        return new SectionCreator<Song>(getActivity(), loader, null);
    }

    @Override
    public void setupNoResultsContainer(NoResultsContainer empty) {
        super.setupNoResultsContainer(empty);

        empty.setMainText(R.string.empty_last_added_main);
        empty.setSecondaryText(R.string.empty_last_added);
    }

    @Override
    public void setupActionBar() {
        ((BaseActivity)getActivity()).setupActionBar(R.string.playlist_last_added);
    }

    @Override
    protected long getFragmentSourceId() {
        return Config.SmartPlaylistType.LastAdded.mId;
    }

    protected SmartPlaylistType getSmartPlaylistType() {
        return Config.SmartPlaylistType.LastAdded;
    }

    @Override
    protected int getShuffleTitleId() { return R.string.menu_shuffle_last_added; }

    @Override
    protected int getClearTitleId() { return R.string.clear_last_added; }

    @Override
    protected void clearList() { MusicUtils.clearLastAdded(getActivity()); }
}