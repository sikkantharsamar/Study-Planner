'use client'

import { createContext, useContext, useEffect, useMemo, useState, type ReactNode } from 'react'

type ThemeMode = 'light' | 'dark' | 'system'
type ActiveTheme = 'light' | 'dark'

type ThemeContextValue = {
  mode: ThemeMode
  activeTheme: ActiveTheme
  setMode: (mode: ThemeMode) => void
}

const THEME_STORAGE_KEY = 'study-planner-theme-mode'
const ThemeContext = createContext<ThemeContextValue | undefined>(undefined)

function getSystemTheme(): ActiveTheme {
  if (typeof window === 'undefined') {
    return 'light'
  }

  return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
}

function applyThemeClass(theme: ActiveTheme) {
  if (typeof document === 'undefined') {
    return
  }

  const html = document.documentElement
  html.classList.toggle('dark', theme === 'dark')
}

export function ThemeProvider({ children }: { children: ReactNode }) {
  const [mode, setModeState] = useState<ThemeMode>('system')
  const [activeTheme, setActiveTheme] = useState<ActiveTheme>('light')

  useEffect(() => {
    if (typeof window === 'undefined') return

    const storedMode = window.localStorage.getItem(THEME_STORAGE_KEY)
    const nextMode: ThemeMode = storedMode === 'light' || storedMode === 'dark' || storedMode === 'system'
      ? storedMode
      : 'system'

    setModeState(nextMode)
    const resolvedTheme = nextMode === 'system' ? getSystemTheme() : nextMode
    setActiveTheme(resolvedTheme)
    applyThemeClass(resolvedTheme)
  }, [])

  useEffect(() => {
    if (typeof window === 'undefined') return

    const media = window.matchMedia('(prefers-color-scheme: dark)')
    const handleSystemThemeChange = () => {
      if (mode !== 'system') return
      const resolvedTheme = getSystemTheme()
      setActiveTheme(resolvedTheme)
      applyThemeClass(resolvedTheme)
    }

    media.addEventListener('change', handleSystemThemeChange)
    return () => media.removeEventListener('change', handleSystemThemeChange)
  }, [mode])

  const setMode = (nextMode: ThemeMode) => {
    setModeState(nextMode)

    if (typeof window !== 'undefined') {
      window.localStorage.setItem(THEME_STORAGE_KEY, nextMode)
    }

    const resolvedTheme = nextMode === 'system' ? getSystemTheme() : nextMode
    setActiveTheme(resolvedTheme)
    applyThemeClass(resolvedTheme)
  }

  const value = useMemo(
    () => ({ mode, activeTheme, setMode }),
    [mode, activeTheme],
  )

  return <ThemeContext.Provider value={value}>{children}</ThemeContext.Provider>
}

export function useTheme() {
  const context = useContext(ThemeContext)
  if (!context) {
    throw new Error('useTheme must be used within a ThemeProvider')
  }
  return context
}
