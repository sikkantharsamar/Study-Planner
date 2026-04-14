import { mkdir, readFile, writeFile } from 'fs/promises'
import path from 'path'
import { NextResponse } from 'next/server'

type AuthUser = {
  id: string
  name: string
  studentId: string
  password: string
}

const dataDir = path.join(process.cwd(), 'dev-data')
const dataFile = path.join(dataDir, 'auth-users.csv')
const header = 'timestamp,id,name,studentId,password\n'

function csvEscape(value: string): string {
  const normalized = value.replace(/\r?\n|\r/g, ' ')
  if (normalized.includes(',') || normalized.includes('"')) {
    return `"${normalized.replace(/"/g, '""')}"`
  }
  return normalized
}

function parseCsvLine(line: string): string[] {
  const values: string[] = []
  let current = ''
  let inQuotes = false

  for (let index = 0; index < line.length; index += 1) {
    const character = line[index]
    const nextCharacter = line[index + 1]

    if (character === '"') {
      if (inQuotes && nextCharacter === '"') {
        current += '"'
        index += 1
      } else {
        inQuotes = !inQuotes
      }
      continue
    }

    if (character === ',' && !inQuotes) {
      values.push(current)
      current = ''
      continue
    }

    current += character
  }

  values.push(current)
  return values
}

async function readUsers(): Promise<AuthUser[]> {
  try {
    const raw = await readFile(dataFile, 'utf8')
    const lines = raw.trim().split(/\r?\n/).filter(Boolean)

    if (lines.length <= 1) {
      return []
    }

    return lines.slice(1).map((line) => {
      const [timestamp = '', id = '', name = '', studentId = '', password = ''] = parseCsvLine(line)
      return {
        id,
        name,
        studentId,
        password,
      }
    })
  } catch {
    return []
  }
}

export async function GET() {
  const users = await readUsers()
  return NextResponse.json({ users })
}

export async function POST(req: Request) {
  try {
    const body = (await req.json()) as { name?: string; studentId?: string; password?: string }
    const name = (body.name ?? '').trim()
    const studentId = (body.studentId ?? '').trim()
    const password = (body.password ?? '').trim()

    if (!name || !studentId || !password) {
      return NextResponse.json({ error: 'Missing required fields' }, { status: 400 })
    }

    await mkdir(dataDir, { recursive: true })
    const users = await readUsers()

    if (users.some((user) => user.studentId === studentId)) {
      return NextResponse.json({ error: 'Student ID already exists' }, { status: 409 })
    }

    const newUser: AuthUser = {
      id: Date.now().toString(),
      name,
      studentId,
      password,
    }

    const row = `${new Date().toISOString()},${csvEscape(newUser.id)},${csvEscape(newUser.name)},${csvEscape(newUser.studentId)},${csvEscape(newUser.password)}\n`
    const content = users.length === 0 ? `${header}${row}` : `${await readFile(dataFile, 'utf8')}${row}`

    await writeFile(dataFile, content, 'utf8')

    return NextResponse.json({ user: newUser }, { status: 201 })
  } catch {
    return NextResponse.json({ error: 'Failed to save user' }, { status: 500 })
  }
}