import { db } from "@/lib/db";
import { Course } from "@/types/Course";
import { Purchase } from "@prisma/client";
import { getCourse } from "./get-chapter";

type PurchaseWithCourse = Purchase & {
  course: Course;
};

const groupByCourse = async (purchases: PurchaseWithCourse[]) => {
  const grouped: { [courseTitle: string]: number } = {};

  for (const purchase of purchases) {
    const course = await getCourse(purchase.courseId);
    if (course) {
      const courseTitle = course.title;
      if (!grouped[courseTitle]) {
        grouped[courseTitle] = 0;
      }
      grouped[courseTitle] += course.price || 0;
    }
  }

  return grouped;
};

export const getAnalytics = async (userId: string, courseId: string) => {
  try {
    const purchases = await db.purchase.findMany({
      where: {
        userId: userId,
        courseId: courseId, // Filter purchases by courseId
      },
    });

    const groupedEarnings = await groupByCourse(purchases);
    const data = Object.entries(groupedEarnings).map(
      ([courseTitle, total]) => ({
        name: courseTitle,
        total: total,
      })
    );

    const totalRevenue = data.reduce((acc, curr) => acc + curr.total, 0);
    const totalSales = purchases.length;

    return {
      data,
      totalRevenue,
      totalSales,
    };
  } catch (error) {
    console.log("[GET_ANALYTICS]", error);
    return {
      data: [],
      totalRevenue: 0,
      totalSales: 0,
    };
  }
};
