package com.example.tktmusicapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Custom Shapes for TKT Music App
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

// Additional Custom Shapes
val TKTMusicShapes = Shapes(
    // Button Shapes
    extraSmall = RoundedCornerShape(4.dp),    // Small buttons, tags
    small = RoundedCornerShape(8.dp),         // Standard buttons
    medium = RoundedCornerShape(12.dp),       // Cards, text fields
    large = RoundedCornerShape(16.dp),        // Large cards, dialogs
    extraLarge = RoundedCornerShape(24.dp)    // Player, album art
)

// Individual Shape Properties for direct use
val ExtraSmallShape = RoundedCornerShape(4.dp)
val SmallShape = RoundedCornerShape(8.dp)
val MediumShape = RoundedCornerShape(12.dp)
val LargeShape = RoundedCornerShape(16.dp)
val ExtraLargeShape = RoundedCornerShape(24.dp)

// Specialized Shapes
val CircleShape = RoundedCornerShape(50) // 50% for circular elements
val PillShape = RoundedCornerShape(50)   // For pill-shaped elements
val BottomSheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
val TopAppBarShape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)

// Player Specific Shapes
val PlayerCardShape = RoundedCornerShape(20.dp)
val SeekBarShape = RoundedCornerShape(4.dp)
val VolumeBarShape = RoundedCornerShape(2.dp)

// Card Variations
val SongCardShape = RoundedCornerShape(12.dp)
val AlbumCardShape = RoundedCornerShape(16.dp)
val ArtistCardShape = RoundedCornerShape(20.dp)
val PlaylistCardShape = RoundedCornerShape(16.dp)

// Input Field Shapes
val TextFieldShape = RoundedCornerShape(12.dp)
val SearchBarShape = RoundedCornerShape(20.dp)

// Button Variations
val PrimaryButtonShape = RoundedCornerShape(12.dp)
val SecondaryButtonShape = RoundedCornerShape(8.dp)
val IconButtonShape = RoundedCornerShape(8.dp)