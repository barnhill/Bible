package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.data.IVersionProvider
import kotlinx.serialization.Serializable

@Serializable
data class VersionsDomain(override val versions: List<IVersion> = emptyList()) : IVersionProvider
