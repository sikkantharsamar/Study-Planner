'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useAuth } from '../context/AuthContext'
import { Subject, useSubjects } from '../context/SubjectContext'

export default function SubjectsPage() {
  const { user } = useAuth()
  const router = useRouter()
  const { subjects, addSubject, addStudyHour, deleteSubject } = useSubjects()

  const [name, setName] = useState('')
  const [instructor, setInstructor] = useState('')
  const [weeklyGoalHours, setWeeklyGoalHours] = useState('')
  const [error, setError] = useState('')

  if (!user) {
    router.push('/login')
    return null
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()

    const goalHours = Number(weeklyGoalHours)
    if (!name.trim() || !instructor.trim() || !weeklyGoalHours || Number.isNaN(goalHours) || goalHours <= 0) {
      setError('Please enter a valid subject name, instructor, and goal hours.')
      return
    }

    addSubject({ name: name.trim(), instructor: instructor.trim(), weeklyGoalHours: goalHours })
    setName('')
    setInstructor('')
    setWeeklyGoalHours('')
    setError('')
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-6xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">Subjects</h1>
            <p className="text-sm text-gray-600">Organize your classes and track weekly study goals.</p>
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
            <h2 className="text-xl font-semibold text-gray-900 mb-4">New Subject</h2>
            <form className="space-y-4" onSubmit={handleSubmit}>
              <div>
                <label htmlFor="name" className="block text-sm font-medium text-gray-700">
                  Subject Name
                </label>
                <input
                  id="name"
                  value={name}
                  onChange={e => setName(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  placeholder="Mathematics"
                />
              </div>

              <div>
                <label htmlFor="instructor" className="block text-sm font-medium text-gray-700">
                  Instructor
                </label>
                <input
                  id="instructor"
                  value={instructor}
                  onChange={e => setInstructor(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  placeholder="Miss. Luna"
                />
              </div>

              <div>
                <label htmlFor="weeklyGoalHours" className="block text-sm font-medium text-gray-700">
                  Weekly Goal Hours
                </label>
                <input
                  id="weeklyGoalHours"
                  type="number"
                  min="1"
                  value={weeklyGoalHours}
                  onChange={e => setWeeklyGoalHours(e.target.value)}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  placeholder="6"
                />
              </div>

              {error && <p className="text-sm text-red-500">{error}</p>}

              <button
                type="submit"
                className="w-full inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none"
              >
                Add Subject
              </button>
            </form>
          </section>

          <section className="lg:col-span-2 bg-white p-6 rounded-xl shadow-sm">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl font-semibold text-gray-900">Your Subjects</h2>
              <span className="text-sm text-gray-500">{subjects.length} subjects</span>
            </div>

            {subjects.length === 0 ? (
              <div className="text-gray-600">No subjects yet. Add one to start planning.</div>
            ) : (
              <div className="space-y-4">
                {subjects.map((subject: Subject) => {
                  const progress = subject.weeklyGoalHours === 0 ? 0 : Math.round((subject.completedHours / subject.weeklyGoalHours) * 100)

                  return (
                    <div key={subject.id} className="rounded-xl border border-gray-200 p-4">
                      <div className="flex items-start justify-between gap-4">
                        <div>
                          <h3 className="text-lg font-semibold text-gray-900">{subject.name}</h3>
                          <p className="text-sm text-gray-600">Instructor: {subject.instructor}</p>
                          <p className="mt-2 text-sm text-gray-500">Weekly goal: {subject.weeklyGoalHours} hours</p>
                        </div>
                        <div className="text-right">
                          <p className="text-sm font-semibold text-indigo-600">{subject.completedHours}/{subject.weeklyGoalHours} hours</p>
                          <p className="text-xs text-gray-500">{progress}% complete</p>
                        </div>
                      </div>

                      <div className="mt-4 h-2 rounded-full bg-gray-100 overflow-hidden">
                        <div
                          className="h-full rounded-full bg-indigo-600"
                          style={{ width: `${Math.min(progress, 100)}%` }}
                        />
                      </div>

                      <div className="mt-4 flex items-center justify-between gap-2">
                        <button
                          onClick={() => addStudyHour(subject.id)}
                          className="px-3 py-2 rounded-md text-sm font-medium bg-indigo-100 text-indigo-700 hover:bg-indigo-200"
                        >
                          Add Study Hour
                        </button>
                        <button
                          onClick={() => deleteSubject(subject.id)}
                          className="px-3 py-2 rounded-md text-sm font-medium bg-red-100 text-red-700 hover:bg-red-200"
                        >
                          Delete
                        </button>
                      </div>
                    </div>
                  )
                })}
              </div>
            )}
          </section>
        </div>
      </div>
    </div>
  )
}
