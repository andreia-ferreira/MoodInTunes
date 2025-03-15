package net.penguin.common_design

import androidx.compose.ui.tooling.preview.Preview

@Preview(showSystemUi = true, device = "spec:parent=3.2in QVGA (ADP2),orientation=portrait", name = "Small Phone - Portrait")
@Preview(showSystemUi = true, device = "spec:parent=3.2in QVGA (ADP2),orientation=landscape", name = "Small Phone - Landscape")
@Preview(showSystemUi = true, device = "spec:parent=pixel_5,orientation=portrait", name = "Normal Phone - Portrait")
@Preview(showSystemUi = true, device = "spec:parent=pixel_5,orientation=landscape", name = "Normal Phone - Landscape")
@Preview(showSystemUi = true, device = "spec:parent=pixel_c,orientation=portrait", name = "Tablet - Portrait")
@Preview(showSystemUi = true, device = "spec:parent=pixel_c,orientation=landscape", name = "Tablet - Landscape")
annotation class DevicePreviews
