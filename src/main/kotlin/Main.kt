package com.ant00000ny

import javax.sound.midi.*
import kotlin.system.exitProcess


data class Note(
    val length: Double,
    val pitch: Int,
)
fun main() {
    MidiSystem.
}


fun playMidi(notes: IntArray, times: LongArray) {
    try {
        // A static method of MidiSystem that returns
        // a sequencer instance.

        val sequencer = MidiSystem.getSequencer()
        sequencer.open()
        println("Sequencer is open.")

        val bpm = 220
        val resolution = 4


        // Creating a sequence.
        val sequence = Sequence(Sequence.PPQ, resolution)


        // PPQ(Pulse per ticks) is used to specify timing
        // type and 4 is the timing resolution.
        val ticksPerSecond = resolution * (bpm / 60.0)
        val tickSize = 1.0 / ticksPerSecond


        // Creating a track on our sequence upon which
        // MIDI events would be placed
        val track: Track = sequence.createTrack()


        // Adding some events to the track
        for (i in notes.indices) {
            val timeFactor = (times[i] / 1000).toDouble()
            // Add Note On event
            track.add(
                makeEvent(
                    ShortMessage.NOTE_ON,
                    1,
                    notes[i],
                    120,
                    ticksPerSecond * timeFactor
                )
            ) //ShortMessage.NOTE_ON 144


            // Add Note Off event
            track.add(
                makeEvent(
                    ShortMessage.NOTE_OFF,
                    1,
                    notes[i],
                    120,
                    (ticksPerSecond * timeFactor) + ticksPerSecond
                )
            ) //ShortMessage.NOTE_OFF 128
        }


        // Setting our sequence so that the sequencer can
        // run it on synthesizer
        sequencer.setSequence(sequence)


        // Specifies the beat rate in beats per minute.
        sequencer.tempoInBPM = bpm.toFloat()


        // Sequencer starts to play notes
        sequencer.start()

        while (true) {
            // Exit the program when sequencer has stopped playing.

            if (!sequencer.isRunning) {
                sequencer.close()
                println("Sequencer is closed.")
                exitProcess(1)
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun makeEvent(
    command: Int,
    channel: Int,
    note: Int,
    velocity: Int,
    tick: Double
): MidiEvent? {
    var event: MidiEvent? = null

    try {
        // ShortMessage stores a note as command type, channel,
        // instrument it has to be played on and its speed.

        val a = ShortMessage()
        a.setMessage(command, channel, note, velocity)

        // A midi event is comprised of a short message(representing
        // a note) and the tick at which that note has to be played
        event = MidiEvent(a, tick.toInt().toLong())
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
    return event
}
