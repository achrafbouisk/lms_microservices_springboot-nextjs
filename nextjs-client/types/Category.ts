import { Course } from "./Course";

interface Category {
  id: string;
  name: string;
  courses: Course[]; // Assuming Course is another TypeScript interface
  createdAt: Date;
  updatedAt: Date;
}

export type { Category };
