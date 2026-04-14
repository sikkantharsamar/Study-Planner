'use client'

import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react'

interface User {
  id: string
  name: string
  studentId: string
  password: string
}

interface AuthContextType {
  user: User | null
  login: (studentId: string, password: string) => Promise<boolean>
  register: (studentId: string, name: string, password: string) => Promise<boolean>
  logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)
const USERS_STORAGE_KEY = 'study-planner-users'
const CURRENT_USER_STORAGE_KEY = 'study-planner-current-user'

export const useAuth = () => {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  return context
}

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [users, setUsers] = useState<User[]>([])
  const [user, setUser] = useState<User | null>(null)
  const [usersLoaded, setUsersLoaded] = useState(false)

  const mergeUsers = (existingUsers: User[], incomingUsers: User[]) => {
    const mergedUsers = new Map<string, User>()

    for (const registeredUser of existingUsers) {
      mergedUsers.set(registeredUser.studentId, registeredUser)
    }

    for (const registeredUser of incomingUsers) {
      mergedUsers.set(registeredUser.studentId, registeredUser)
    }

    return Array.from(mergedUsers.values())
  }

  useEffect(() => {
    if (typeof window === 'undefined') return

    const storedUsers = window.localStorage.getItem(USERS_STORAGE_KEY)
    const storedUser = window.localStorage.getItem(CURRENT_USER_STORAGE_KEY)

    if (storedUsers) {
      try {
        setUsers(JSON.parse(storedUsers) as User[])
      } catch {
        window.localStorage.removeItem(USERS_STORAGE_KEY)
      }
    }

    if (storedUser) {
      try {
        setUser(JSON.parse(storedUser) as User)
      } catch {
        window.localStorage.removeItem(CURRENT_USER_STORAGE_KEY)
      }
    }
  }, [])

  useEffect(() => {
    if (typeof window === 'undefined') return

    window.localStorage.setItem(USERS_STORAGE_KEY, JSON.stringify(users))
  }, [users])

  useEffect(() => {
    if (typeof window === 'undefined') return

    if (user) {
      window.localStorage.setItem(CURRENT_USER_STORAGE_KEY, JSON.stringify(user))
    } else {
      window.localStorage.removeItem(CURRENT_USER_STORAGE_KEY)
    }
  }, [user])

  const loadUsersFromServer = async (): Promise<User[]> => {
    const response = await fetch('/api/dev/auth-users')
    if (!response.ok) {
      throw new Error('Failed to load users')
    }

    const data = (await response.json()) as { users: User[] }
    return data.users
  }

  const ensureUsersLoaded = async (): Promise<User[]> => {
    const serverUsers = await loadUsersFromServer()
    const mergedUsers = mergeUsers(users, serverUsers)
    setUsers(mergedUsers)
    setUsersLoaded(true)
    return mergedUsers
  }

  useEffect(() => {
    const loadUsers = async () => {
      try {
        const serverUsers = await loadUsersFromServer()
        setUsers(previousUsers => mergeUsers(previousUsers, serverUsers))
      } catch {
        // Keep any locally stored users if the server file is unavailable.
      } finally {
        setUsersLoaded(true)
      }
    }

    void loadUsers()
  }, [])

  const ensureUsersLoaded = async (): Promise<User[]> => {
    if (usersLoaded) {
      return users
    }

    const serverUsers = await loadUsersFromServer()
    const mergedUsers = mergeUsers(users, serverUsers)
    setUsers(mergedUsers)
    setUsersLoaded(true)
    return mergedUsers
  }

  const register = async (studentId: string, name: string, password: string): Promise<boolean> => {
    const currentUsers = await ensureUsersLoaded()

    if (currentUsers.some((registeredUser) => registeredUser.studentId === studentId)) {
      return false
    }

    const response = await fetch('/api/dev/auth-users', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ studentId, name, password }),
    })

    if (!response.ok) {
      return false
    }

    const data = (await response.json()) as { user: User }
    setUsers(prev => mergeUsers(prev, [data.user]))
    return true
  }

  const login = async (studentId: string, password: string): Promise<boolean> => {
    const currentUsers = await ensureUsersLoaded()

    const foundUser = currentUsers.find(u => u.studentId === studentId && u.password === password)
    if (foundUser) {
      setUser(foundUser)
      return true
    }
    return false
  }

  const logout = () => {
    setUser(null)
  }

  return (
    <AuthContext.Provider value={{ user, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  )
}