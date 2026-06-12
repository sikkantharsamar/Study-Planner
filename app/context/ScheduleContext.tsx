'use client'

import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react'
import { useAuth } from './AuthContext'

export interface ScheduleItem {
  id: string
  title: string
  subject: string
  day: string
  startTime: string
  endTime: string
}

interface ScheduleContextType {
  schedules: ScheduleItem[]
  addSchedule: (schedule: Omit<ScheduleItem, 'id'>) => void
  deleteSchedule: (scheduleId: string) => void
}

const ScheduleContext = createContext<ScheduleContextType | undefined>(undefined)
const STORAGE_KEY = 'study-planner-schedules'

export const useSchedules = () => {
  const context = useContext(ScheduleContext)
  if (!context) {
    throw new Error('useSchedules must be used within a ScheduleProvider')
  }
  return context
}

export const ScheduleProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const { user } = useAuth()
  const [schedulesByUser, setSchedulesByUser] = useState<Record<string, ScheduleItem[]>>({})

  useEffect(() => {
    if (typeof window === 'undefined') return

    const storedSchedules = window.localStorage.getItem(STORAGE_KEY)
    if (storedSchedules) {
      try {
        setSchedulesByUser(JSON.parse(storedSchedules) as Record<string, ScheduleItem[]>)
      } catch {
        window.localStorage.removeItem(STORAGE_KEY)
      }
    }
  }, [])

  useEffect(() => {
    if (typeof window === 'undefined') return

    window.localStorage.setItem(STORAGE_KEY, JSON.stringify(schedulesByUser))
  }, [schedulesByUser])

  const userId = user?.id
  const schedules = userId ? schedulesByUser[userId] ?? [] : []

  const addSchedule = (scheduleData: Omit<ScheduleItem, 'id'>) => {
    if (!userId) return

    const newSchedule: ScheduleItem = {
      id: Date.now().toString(),
      ...scheduleData,
    }

    setSchedulesByUser(prev => ({
      ...prev,
      [userId]: [newSchedule, ...(prev[userId] ?? [])],
    }))
  }

  const deleteSchedule = (scheduleId: string) => {
    if (!userId) return

    setSchedulesByUser(prev => ({
      ...prev,
      [userId]: (prev[userId] ?? []).filter(schedule => schedule.id !== scheduleId),
    }))
  }

  return (
    <ScheduleContext.Provider value={{ schedules, addSchedule, deleteSchedule }}>
      {children}
    </ScheduleContext.Provider>
  )
}
