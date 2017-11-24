# Notes App
Android development by example

![Notes List](screenshots/noteslist_screenshot1.png)


# Anatomy of the app

* There are two main activities: [NotesListActivity](link) which displays all the notes and [NoteDetailActivity](link) which dispalys a single note.
* The [Note.java](link) is the model for notes data and a collection of it is provided by data sources that implement the [IDataSource](link) interface.
* The `NotesListActivity` is using a [RecyclerView](link) to render the notes.
* More to come later...

# Anatomy by Android Topics

- [Getting started with Android](link)
- [Activity lifecycle](link)
- [XML and Android layouts](link)
- [Android Resources](link)
- [Views and ViewGroups](link)
- [Intents](link)
- [Menus](link)
- [SharedPreferences](link)
- [Settings and PreferenceActivity](link)
- [RecyclerView](link)
- [Fragments](link)
- [AsyncTasks](link)
- [Testing](link)



# TODOs (/feature reqeusts)

Here is a list of possible enhancements to the app. It would be great to update this Readme with documentation around any feature that is added (and also remove it from this list). Currently accepting pull requests :) 

* Add visual cue to the recycling behavior of NotesListActivity's RecyclerView with an option to turn it off in Settings
* Show empty list icon when recycler view data source is empty
* Add sqlite storage data source
* Add Contextual menu to NoteDetailActivity to allow delete, archive,and sharing of a note
* Allow user to archive notes by swiping them away
* Add search icon and search capability to NotesListActivity so users can search all their notes
* Add Archived notes view where user can view notes that were archived and possibly restore or delete them.
* Allow user to embed images into the body of notes and optionally display the image in the note item's CardView.
* Add Internationalization (i18n)
* Add splashscreen 
* Allow app to be invoked by other apps using share intent
* Add multiple configurable themes to the app.
* Add support for multiple note types like Todo (that can be marked as done) and reminders (that rings and once done disappears)
* Your ingenious idea here...


# How can I build this app from scratch?

Building an app from scratch to finish is by far the most instrumental way of learning android. Using this app as a reference means you won't get *stuck* on a problem for too long! 

Here is an attempt to breakdown the entire app into smaller tasks:

* **Create a new android studio project for your app**: 
    - You can give it a different name from "Notes app". 
    - Rename the `MainActivity` to `NotesListActivity`
    - Ensure the app builds and that you were able to launch it in an emulator without any errors.

* **Create and inflate the OptionsMenu for NotesList**:
    - Include a "New Note" action in the options menu
    - Add click handlers for the option menu

* **Create the NoteDetailActivity**:
    - Start this activity when user clicks on "New Note" in NoteList option menu

