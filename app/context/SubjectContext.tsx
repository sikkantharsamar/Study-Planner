'use client'

import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react'
import { useAuth } from './AuthContext'

export interface Subject {
  id: string
  name: string
  instructor: string
  weeklyGoalHours: number
  completedHours: number
}

interface SubjectContextType {
  subjects: Subject[]
  addSubject: (subject: Omit<Subject, 'id' | 'completedHours'>) => void
  addStudyHour: (subjectId: string) => void
  deleteSubject: (subjectId: string) => void
}

const SubjectContext = createContext<SubjectContextType | undefined>(undefined)
const STORAGE_KEY = 'study-planner-subjects'

export const useSubjects = () => {
  const context = useContext(SubjectContext)
  if (!context) {
    throw new Error('useSubjects must be used within a SubjectProvider')
  }
  return context
}

export const SubjectProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const { user } = useAuth()
  const [subjectsByUser, setSubjectsByUser] = useState<Record<string, Subject[]>>({})

  useEffect(() => {
    if (typeof window === 'undefined') return

    const storedSubjects = window.localStorage.getItem(STORAGE_KEY)
    if (storedSubjects) {
      try {
        setSubjectsByUser(JSON.parse(storedSubjects) as Record<string, Subject[]>)
      } catch {
        window.localStorage.removeItem(STORAGE_KEY)
      }
    }
  }, [])

  useEffect(() => {
    if (typeof window === 'undefined') return

    window.localStorage.setItem(STORAGE_KEY, JSON.stringify(subjectsByUser))
  }, [subjectsByUser])

  const userId = user?.id
  const subjects = userId ? subjectsByUser[userId] ?? [] : []

  const addSubject = (subjectData: Omit<Subject, 'id' | 'completedHours'>) => {
    if (!userId) return

    const newSubject: Subject = {
      id: Date.now().toString(),
      completedHours: 0,
      ...subjectData,
    }

    setSubjectsByUser(prev => ({
      ...prev,
      [userId]: [newSubject, ...(prev[userId] ?? [])],
    }))
  }

  const addStudyHour = (subjectId: string) => {
    if (!userId) return

    setSubjectsByUser(prev => ({
      ...prev,
      [userId]: (prev[userId] ?? []).map(subject =>
        subject.id === subjectId && subject.completedHours < subject.weeklyGoalHours
          ? { ...subject, completedHours: subject.completedHours + 1 }
          : subject
      ),
    }))
  }

  const deleteSubject = (subjectId: string) => {
    if (!userId) return

    setSubjectsByUser(prev => ({
      ...prev,
      [userId]: (prev[userId] ?? []).filter(subject => subject.id !== subjectId),
    }))
  }

  return (
    <SubjectContext.Provider value={{ subjects, addSubject, addStudyHour, deleteSubject }}>
      {children}
    </SubjectContext.Provider>
  )
}