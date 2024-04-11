import { UserProgress } from './UserProgress';

interface Chapter {
    id: string;
    title: string;
    description: string;
    videoUrl: string;
    position: number;
    isPublished: boolean;
    isFree: boolean;
    courseId: string;
    muxDataId: string;
    userProgress: UserProgress[];
    createdAt: Date;
    updatedAt: Date;
}

export type { Chapter };