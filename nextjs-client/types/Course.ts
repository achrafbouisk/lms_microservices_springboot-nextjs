import { Category } from "./Category";

interface Course {
  id: string;
  userId: string;
  title: string;
  description: string;
  imageUrl: string;
  price: number;
  isPublished: boolean;
  category: Category; // Assuming Category is another TypeScript interface
  createdAt: Date;
  updatedAt: Date;
}

export type { Course };
