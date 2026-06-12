'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import { useAuth } from '../context/AuthContext'
import { StudySession, useStudySessions } from '../context/StudySessionContext'

export default function SessionsPage() {
  const { user } = useAuth()
  const router = useRouter()
  const { sessions, addSession, deleteSession } = useStudySessions()

  const [subject, setSubject] = useState('')
  const [date, setDate] = useState('')
  const [durationMinutes, setDurationMinutes] = useState('')
  const [notes, setNotes] = useState('')
  const [error, setError] = useState('')

  useEffect(() => {
    if (!user) {
      router.push('/login')
    }
  }, [user, router])

  if (!user) {
    return <div>Loading...</div>
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    const duration = Number(durationMinutes)

    if (!subject.trim() || !date || Number.isNaN(duration) || duration <= 0) {
      setError('Please add a valid subject, date, and duration.')
      return
    }

    addSession({
      subject: subject.trim(),
      date,
      durationMinutes: duration,
      notes: notes.trim(),
    })

    setSubject('')
    setDate('')
    setDurationMinutes('')
    setNotes('')
    setError('')
  }

  const totalMinutes = sessions.reduce((sum, session) => sum + session.durationMinutes, 0)

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-6xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">Study Sessions</h1>
            <p className="text-sm text-gray-600">Track every focused study block.</p>
          </div>
          <button
            onClick={() => router.push('/dashboard')}
            className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md text-sm font-medium"
          >
            Back to Dashboard
          </button>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <section className="lg:col-span-1 bg-white p-6 rounded-xl shadow-sm">
            <h2 className="text-xl font-semibold text-gray-900 mb-4">New Session</h2>
            <form className="space-y-4" onSubmit={handleSubmit}>
              <div>
                <label htmlFor="subject" className="block text-sm font-medium text-gray-700">Subject</label>
                <input
                  id="subject"
                  value={subject}
                  onChange={e => setSubject(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  placeholder="Mathematics"
                />
              </div>

              <div>
                <label htmlFor="date" className="block text-sm font-medium text-gray-700">Date</label>
                <input
                  id="date"
                  type="date"
                  value={date}
                  onChange={e => setDate(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                />
              </div>

              <div>
                <label htmlFor="duration" className="block text-sm font-medium text-gray-700">Duration (minutes)</label>
                <input
                  id="duration"
                  type="number"
                  min="1"
                  value={durationMinutes}
                  onChange={e => setDurationMinutes(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  placeholder="60"
                />
              </div>

              <div>
                <label htmlFor="notes" className="block text-sm font-medium text-gray-700">Notes (optional)</label>
                <textarea
                  id="notes"
                  rows={3}
                  value={notes}
                  onChange={e => setNotes(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                />
              </div>

              {error && <p className="text-sm text-red-500">{error}</p>}

              <button
                type="submit"
                className="w-full inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700"
              >
                Add Session
              </button>
            </form>
          </section>

          <section className="lg:col-span-2 bg-white p-6 rounded-xl shadow-sm">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl font-semibold text-gray-900">Session History</h2>
              <span className="text-sm text-gray-500">Total: {totalMinutes} minutes</span>
            </div>

            {sessions.length === 0 ? (
              <div className="text-gray-600">No sessions yet. Add one to start tracking.</div>
            ) : (
              <div className="space-y-4">
                {sessions.map((session: StudySession) => (
                  <div key={session.id} className="rounded-xl border border-gray-200 p-4">
                    <div className="flex items-start justify-between gap-4">
                      <div>
                        <h3 className="text-lg font-semibold text-gray-900">{session.subject}</h3>
                        <p className="text-sm text-gray-600">{session.date} • {session.durationMinutes} minutes</p>
                        {session.notes && <p className="mt-2 text-sm text-gray-500">{session.notes}</p>}
                      </div>
                      <button
                        onClick={() => deleteSession(session.id)}
                        className="px-3 py-2 rounded-md text-sm font-medium bg-red-100 text-red-700 hover:bg-red-200"
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </section>
        </div>
      </div>
    </div>
  )
}
