import { Chapter } from "./Chapter";

interface UserProgress {
    id: string;
    userId: string;
    isCompleted: boolean;
    chapter: Chapter; // Assuming Chapter is another TypeScript interface
    createdAt: Date;
    updatedAt: Date;
}

export type { UserProgress };
