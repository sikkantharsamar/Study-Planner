'use client'

import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react'
import { useAuth } from './AuthContext'

export interface StudySession {
  id: string
  subject: string
  date: string
  durationMinutes: number
  notes: string
}

interface StudySessionContextType {
  sessions: StudySession[]
  addSession: (session: Omit<StudySession, 'id'>) => void
  deleteSession: (sessionId: string) => void
}

const StudySessionContext = createContext<StudySessionContextType | undefined>(undefined)
const STORAGE_KEY = 'study-planner-sessions'

export const useStudySessions = () => {
  const context = useContext(StudySessionContext)
  if (!context) {
    throw new Error('useStudySessions must be used within a StudySessionProvider')
  }
  return context
}

export const StudySessionProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const { user } = useAuth()
  const [sessionsByUser, setSessionsByUser] = useState<Record<string, StudySession[]>>({})

  useEffect(() => {
    if (typeof window === 'undefined') return

    const storedSessions = window.localStorage.getItem(STORAGE_KEY)
    if (storedSessions) {
      try {
        setSessionsByUser(JSON.parse(storedSessions) as Record<string, StudySession[]>)
      } catch {
        window.localStorage.removeItem(STORAGE_KEY)
      }
    }
  }, [])

  useEffect(() => {
    if (typeof window === 'undefined') return

    window.localStorage.setItem(STORAGE_KEY, JSON.stringify(sessionsByUser))
  }, [sessionsByUser])

  const userId = user?.id
  const sessions = userId ? sessionsByUser[userId] ?? [] : []

  const addSession = (sessionData: Omit<StudySession, 'id'>) => {
    if (!userId) return

    const newSession: StudySession = {
      id: Date.now().toString(),
      ...sessionData,
    }

    setSessionsByUser(prev => ({
      ...prev,
      [userId]: [newSession, ...(prev[userId] ?? [])],
    }))
  }

  const deleteSession = (sessionId: string) => {
    if (!userId) return

    setSessionsByUser(prev => ({
      ...prev,
      [userId]: (prev[userId] ?? []).filter(session => session.id !== sessionId),
    }))
  }

  return (
    <StudySessionContext.Provider value={{ sessions, addSession, deleteSession }}>
      {children}
    </StudySessionContext.Provider>
  )
}
