package net.penguin.common_design.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import net.penguin.common_design.R

val PaddingSmall @Composable get() = dimensionResource(id = R.dimen.padding_small)
val PaddingDefault @Composable get() = dimensionResource(id = R.dimen.padding_default)
val PaddingBig @Composable get() = dimensionResource(id = R.dimen.padding_big)
val CornerRadiusDefault @Composable get() = dimensionResource(id = R.dimen.corner_radius_default)
val ContentMaxWidth @Composable get() = dimensionResource(id = R.dimen.content_max_width)
