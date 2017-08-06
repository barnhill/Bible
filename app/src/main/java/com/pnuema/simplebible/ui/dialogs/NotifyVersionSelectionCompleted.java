package com.pnuema.simplebible.ui.dialogs;

import com.pnuema.simplebible.data.Versions;

public interface NotifyVersionSelectionCompleted {
    void onSelectionComplete(Versions.Version version);
}
