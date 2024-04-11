import axios from "axios";

export async function getPublishedChapters(courseId: string) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/chapters/published-chapters/${courseId}`
    );

    return response.data;
  } catch (error) {
    console.error("Error fetching course:", error);
    throw error;
  }
}

export async function countUserProgress(userId: string, chapterIds: string[]) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/user-progress/count-valid-completed-chapters?userId=${userId}&publishedChapterIds=${chapterIds}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching course:", error);
    throw error;
  }
}

export const getProgress = async (
  userId: string,
  courseId: string
): Promise<number> => {
  try {
    const publishedChapters = await getPublishedChapters(courseId);

    const publishedChapterIds = publishedChapters.map(
      (chapter: any) => chapter.id
    );

    const validCompletedChapters = await countUserProgress(
      userId,
      publishedChapterIds
    );

    const progressPercentage =
      (validCompletedChapters / publishedChapterIds.length) * 100;

    return progressPercentage;
  } catch (error) {
    console.log("[GET_PROGRESS]", error);
    return 0;
  }
};
