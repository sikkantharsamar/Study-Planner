import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import './globals.css'
import { AuthProvider } from './context/AuthContext'
import { SubjectProvider } from './context/SubjectContext'
import { TaskProvider } from './context/TaskContext'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'Study Planner',
  description: 'Smart Study Planner Web App',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <AuthProvider>
          <SubjectProvider>
            <TaskProvider>
              {children}
            </TaskProvider>
          </SubjectProvider>
        </AuthProvider>
      </body>
    </html>
  )
}