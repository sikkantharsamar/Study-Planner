'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import { useAuth } from '../context/AuthContext'
import { ScheduleItem, useSchedules } from '../context/ScheduleContext'

const dayOptions = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']

export default function SchedulesPage() {
  const { user } = useAuth()
  const router = useRouter()
  const { schedules, addSchedule, deleteSchedule } = useSchedules()

  const [title, setTitle] = useState('')
  const [subject, setSubject] = useState('')
  const [day, setDay] = useState('Monday')
  const [startTime, setStartTime] = useState('')
  const [endTime, setEndTime] = useState('')
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

    if (!title.trim() || !subject.trim() || !startTime || !endTime) {
      setError('Please fill in all schedule fields.')
      return
    }

    if (startTime >= endTime) {
      setError('End time must be later than start time.')
      return
    }

    addSchedule({
      title: title.trim(),
      subject: subject.trim(),
      day,
      startTime,
      endTime,
    })

    setTitle('')
    setSubject('')
    setDay('Monday')
    setStartTime('')
    setEndTime('')
    setError('')
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-6xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">Schedules</h1>
            <p className="text-sm text-gray-600">Plan recurring weekly study blocks.</p>
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
            <h2 className="text-xl font-semibold text-gray-900 mb-4">New Schedule</h2>
            <form className="space-y-4" onSubmit={handleSubmit}>
              <div>
                <label htmlFor="title" className="block text-sm font-medium text-gray-700">Title</label>
                <input
                  id="title"
                  value={title}
                  onChange={e => setTitle(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  placeholder="Morning Revision"
                />
              </div>

              <div>
                <label htmlFor="subject" className="block text-sm font-medium text-gray-700">Subject</label>
                <input
                  id="subject"
                  value={subject}
                  onChange={e => setSubject(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  placeholder="Computer Science"
                />
              </div>

              <div>
                <label htmlFor="day" className="block text-sm font-medium text-gray-700">Day</label>
                <select
                  id="day"
                  value={day}
                  onChange={e => setDay(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 bg-white shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                >
                  {dayOptions.map(option => (
                    <option key={option} value={option}>{option}</option>
                  ))}
                </select>
              </div>

              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label htmlFor="startTime" className="block text-sm font-medium text-gray-700">Start</label>
                  <input
                    id="startTime"
                    type="time"
                    value={startTime}
                    onChange={e => setStartTime(e.target.value)}
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  />
                </div>
                <div>
                  <label htmlFor="endTime" className="block text-sm font-medium text-gray-700">End</label>
                  <input
                    id="endTime"
                    type="time"
                    value={endTime}
                    onChange={e => setEndTime(e.target.value)}
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  />
                </div>
              </div>

              {error && <p className="text-sm text-red-500">{error}</p>}

              <button
                type="submit"
                className="w-full inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700"
              >
                Add Schedule
              </button>
            </form>
          </section>

          <section className="lg:col-span-2 bg-white p-6 rounded-xl shadow-sm">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl font-semibold text-gray-900">Weekly Plan</h2>
              <span className="text-sm text-gray-500">{schedules.length} entries</span>
            </div>

            {schedules.length === 0 ? (
              <div className="text-gray-600">No schedules yet. Add one to build your week.</div>
            ) : (
              <div className="space-y-4">
                {schedules.map((schedule: ScheduleItem) => (
                  <div key={schedule.id} className="rounded-xl border border-gray-200 p-4">
                    <div className="flex items-start justify-between gap-4">
                      <div>
                        <h3 className="text-lg font-semibold text-gray-900">{schedule.title}</h3>
                        <p className="text-sm text-gray-600">{schedule.subject}</p>
                        <p className="mt-2 text-sm text-gray-500">{schedule.day} • {schedule.startTime} - {schedule.endTime}</p>
                      </div>
                      <button
                        onClick={() => deleteSchedule(schedule.id)}
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
