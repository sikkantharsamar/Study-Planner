'use client'

import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react'
import { useAuth } from './AuthContext'

export type TaskPriority = 'HIGH' | 'MEDIUM' | 'LOW'

export interface Task {
  id: string
  title: string
  description: string
  priority: TaskPriority
  dueDate: string
  completed: boolean
}

interface TaskContextType {
  tasks: Task[]
  addTask: (task: Omit<Task, 'id' | 'completed'>) => void
  toggleTaskCompletion: (taskId: string) => void
  deleteTask: (taskId: string) => void
}

const TaskContext = createContext<TaskContextType | undefined>(undefined)
const STORAGE_KEY = 'study-planner-tasks'

export const useTasks = () => {
  const context = useContext(TaskContext)
  if (!context) {
    throw new Error('useTasks must be used within a TaskProvider')
  }
  return context
}

export const TaskProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const { user } = useAuth()
  const [tasksByUser, setTasksByUser] = useState<Record<string, Task[]>>({})

  useEffect(() => {
    if (typeof window === 'undefined') return

    const storedTasks = window.localStorage.getItem(STORAGE_KEY)
    if (storedTasks) {
      try {
        setTasksByUser(JSON.parse(storedTasks) as Record<string, Task[]>)
      } catch {
        window.localStorage.removeItem(STORAGE_KEY)
      }
    }
  }, [])

  useEffect(() => {
    if (typeof window === 'undefined') return

    window.localStorage.setItem(STORAGE_KEY, JSON.stringify(tasksByUser))
  }, [tasksByUser])

  const userId = user?.id
  const tasks = userId ? tasksByUser[userId] ?? [] : []

  const addTask = (taskData: Omit<Task, 'id' | 'completed'>) => {
    if (!userId) return
    const newTask: Task = {
      id: Date.now().toString(),
      completed: false,
      ...taskData,
    }
    setTasksByUser(prev => ({
      ...prev,
      [userId]: [newTask, ...(prev[userId] ?? [])],
    }))
  }

  const toggleTaskCompletion = (taskId: string) => {
    if (!userId) return
    setTasksByUser(prev => ({
      ...prev,
      [userId]: prev[userId].map(task =>
        task.id === taskId ? { ...task, completed: !task.completed } : task
      ),
    }))
  }

  const deleteTask = (taskId: string) => {
    if (!userId) return
    setTasksByUser(prev => ({
      ...prev,
      [userId]: prev[userId].filter(task => task.id !== taskId),
    }))
  }

  return (
    <TaskContext.Provider value={{ tasks, addTask, toggleTaskCompletion, deleteTask }}>
      {children}
    </TaskContext.Provider>
  )
}
