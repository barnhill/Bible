package com.pnuema.simplebible.ui.dialogs;

import com.pnuema.simplebible.data.Versions;

public interface VersionSelectionListener {
    void onVersionSelected(Versions.Version version);
}