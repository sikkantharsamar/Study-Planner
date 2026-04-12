'use client'

import React, { createContext, useContext, useState, ReactNode } from 'react'

interface User {
  id: string
  name: string
  studentId: string
  password: string
}

interface AuthContextType {
  user: User | null
  login: (studentId: string, password: string) => boolean
  register: (studentId: string, name: string, password: string) => boolean
  logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

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

  const register = (studentId: string, name: string, password: string): boolean => {
    if (users.find(u => u.studentId === studentId)) {
      return false // Already exists
    }
    const newUser: User = { id: Date.now().toString(), studentId, name, password }
    setUsers([...users, newUser])
    return true
  }

  const login = (studentId: string, password: string): boolean => {
    const foundUser = users.find(u => u.studentId === studentId && u.password === password)
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