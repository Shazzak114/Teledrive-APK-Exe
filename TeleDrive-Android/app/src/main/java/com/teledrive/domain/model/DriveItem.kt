package com.teledrive.domain.model

sealed class DriveItem {
    data class Folder(val id: Long, val name: String) : DriveItem()
    data class File(val id: Long, val name: String, val size: Long) : DriveItem()
}
