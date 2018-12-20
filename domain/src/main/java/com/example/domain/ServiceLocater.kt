package com.example.domain

import com.example.domain.repository.*

/**
 * Created by Festus Kiambi on 12/3/18.
 */

class NoteServiceLocator(val localAnon: ILocalNoteRepository,
                         val remoteReg: IRemoteNoteRepository,
                         val transactionReg: ITransactionRepository
)