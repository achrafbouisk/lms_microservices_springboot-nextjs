import { getProgress } from "@/actions/get-progress";
import { db } from "@/lib/db";
import { Category } from "@/types/Category";
import { Course } from "@/types/Course";
import axios from "axios";

type courseWithProgressWithCategory = Course & {
  category: Category | null;
  chapters: { id: string }[];
  progress: number | null;
};

type Getcourses = {
  userId: string;
  title?: string;
  categoryId?: string;
};

export async function getCourse(courseId: string) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/courses/get-course-with-published-chapters/${courseId}`
    );
    return response.data.body;
  } catch (error) {
    console.error("Error fetching course:", error);
    throw error;
  }
}


export async function getCoursesApi(title: string, categoryId: string) {
  try {
    let response;
    if (title) {
      response = await axios.get(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/courses/v2?title=${title}`
      );
    } else if (categoryId) {
      response = await axios.get(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/courses/v2?categoryId=${categoryId}`
      );
    } else {
      response = await axios.get(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/courses`
      );
    }
    return response.data;
  } catch (error) {
    console.error("Error fetching course:", error);
    throw error;
  }
}

export async function getPurchases(courseId: string) {
  try {
    const purchases = await db.purchase.findMany({
      where: {
        courseId,
      },
    });
    return purchases;
  } catch (error) {
    console.error("Error fetching course:", error);
    throw error;
  }
}

export const getCourses = async ({
  userId,
  title,
  categoryId,
}: Getcourses): Promise<courseWithProgressWithCategory[]> => {
  try {
    const courses = await getCoursesApi(title, categoryId);

    const coursesWithProgress: courseWithProgressWithCategory[] =
      await Promise.all(
        courses.map(async (course: any) => {
          const purchases = await getPurchases(course.id);
          if (purchases.length === 0) {
            return {
              ...course,
              progress: null,
            };
          }

          const progressPercentage = await getProgress(userId, course.id);

          return {
            ...course,
            progress: progressPercentage,
          };
        })
      );
    return coursesWithProgress;
  } catch (error) {
    console.log("[GET_courseS]", error);
    return [];
  }
};
