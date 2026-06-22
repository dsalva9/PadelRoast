package com.example.data

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File

class AudioRecorder(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var currentFile: File? = null

    /**
     * Starts recording audio to a temporary file in cache.
     * Returns the File object where recording is stored, or null if initialization fails.
     */
    fun startRecording(): File? {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, "roast_dictation.m4a")
        currentFile = file

        try {
            // Delete previous file if it exists to avoid overwrites
            if (file.exists()) {
                file.delete()
            }

            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(file.absolutePath)
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            releaseRecorder()
            return null
        }
        return file
    }

    /**
     * Stops recording and returns the final File.
     */
    fun stopRecording(): File? {
        try {
            mediaRecorder?.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            releaseRecorder()
        }
        return currentFile
    }

    /**
     * Cleans up the temporary audio file.
     */
    fun cleanUp() {
        currentFile?.let {
            if (it.exists()) {
                it.delete()
            }
        }
        currentFile = null
    }

    private fun releaseRecorder() {
        mediaRecorder?.apply {
            try {
                reset()
                release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        mediaRecorder = null
    }
}
