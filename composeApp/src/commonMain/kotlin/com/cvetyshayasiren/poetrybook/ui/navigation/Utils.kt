package com.cvetyshayasiren.poetrybook.ui.navigation

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

@Composable
fun WindowSizeClass.Companion.isExpanded(): Boolean = currentWindowAdaptiveInfo()
    .windowSizeClass
    .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)