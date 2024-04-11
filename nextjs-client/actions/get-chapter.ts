import { db } from "@/lib/db";
import { Attachment } from "@/types/Attachment";
import { Chapter } from "@/types/Chapter";
import axios from "axios";

interface GetChapterProps {
  userId: string;
  courseId: string;
  chapterId: string;
}

export async function getCourse(courseId: string) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/courses/get-course-chapter/${courseId}`
    );
    return response.data.body;
  } catch (error) {
    console.error("Error fetching course:", error);
    throw error;
  }
}

export async function getChapterById(chapterId: string) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/chapters/chapter/${chapterId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching chapter:", error);
    throw error;
  }
}

export async function getNextChapter(
  courseId: string,
  currentPosition: string
) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/chapters/courses/${courseId}/next-chapter/${currentPosition}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching chapter:", error);
    throw error;
  }
}

export async function getAttachmentByCourseId(courseId: string) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/attachments/${courseId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching attachments:", error);
    throw error;
  }
}

export async function getUserProgress(chapterId: string, userId: string) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/user-progress/${chapterId}/progress/${userId}`
    );
    return response.data.body;
  } catch (error) {
    console.error("Error fetching user progress:", error);
    throw error;
  }
}

export async function getMuxData(chapterId: string) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/mux-data/${chapterId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching user progress:", error);
    throw error;
  }
}

export const getChapter = async ({
  userId,
  courseId,
  chapterId,
}: GetChapterProps) => {
  try {
    const purchase = await db.purchase.findUnique({
      where: {
        userId_courseId: {
          userId,
          courseId,
        },
      },
    });

    const course = await getCourse(courseId);

    const chapter = await getChapterById(chapterId);

    if (!chapter || !course) {
      throw new Error("Chapter or course not found");
    }

    let muxData = null;
    let attachments: Attachment[] = [];
    let nextChapter: Chapter | null = null;

    if (purchase) {
      attachments = await getAttachmentByCourseId(courseId);

      if (chapter.isFree || purchase) {
        muxData = await getMuxData(chapter.id);
        nextChapter = await getNextChapter(courseId, chapter.position);
      }
    }

    const userProgress = await getUserProgress(chapterId, userId);

    return {
      chapter,
      course,
      muxData,
      attachments,
      nextChapter,
      userProgress,
      purchase,
    };
  } catch (error) {
    console.log("[GET_CHAPTER]", error);
    return {
      chapter: null,
      course: null,
      muxData: null,
      attachments: [],
      nextChapter: null,
      userProgress: null,
      purchase: null,
    };
  }
};
