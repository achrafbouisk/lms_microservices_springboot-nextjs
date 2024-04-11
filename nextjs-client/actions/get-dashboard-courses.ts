import { db } from "@/lib/db";
import { getProgress } from "@/actions/get-progress";
import { Course } from "@/types/Course";
import { Category } from "@/types/Category";
import { Chapter } from "@/types/Chapter";
import { getCourse } from "./get-chapter"; // Assuming getCourse function is implemented

type CourseWithProgressWithCategory = Course & {
  category: Category;
  chapters: Chapter[];
  progress: number | null;
};

type DashboardCourses = {
  completedCourses: CourseWithProgressWithCategory[];
  coursesInProgress: CourseWithProgressWithCategory[];
};

export const getDashboardcourses = async (
  userId: string
): Promise<DashboardCourses> => {
  try {
    const purchasedCourses = await db.purchase.findMany({
      where: {
        userId: userId,
      },
      select: {
        courseId: true,
      },
    });

    const courses: CourseWithProgressWithCategory[] = [];
    for (const purchase of purchasedCourses) {
      const course = await getCourse(purchase.courseId);
      if (course) {
        const progress = await getProgress(userId, course.id);
        courses.push({ ...course, progress });
      }
    }

    const completedCourses = courses.filter(
      (course) => course.progress === 100
    );
    const coursesInProgress = courses.filter(
      (course) => (course.progress ?? 0) < 100
    );

    return {
      completedCourses,
      coursesInProgress,
    };
  } catch (error) {
    console.log("[GET_DASHBOARD_COURSES]", error);
    return {
      completedCourses: [],
      coursesInProgress: [],
    };
  }
};
