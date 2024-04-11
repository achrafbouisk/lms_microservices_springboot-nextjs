import { auth } from "@clerk/nextjs";
import { redirect } from "next/navigation";

import { db } from "@/lib/db";
import { CourseProgress } from "@/components/course-progress";

import { CourseSidebarItem } from "./course-sidebar-item";
import { Course } from "@/types/Course";
import { Chapter } from "@/types/Chapter";
import { UserProgress } from "@/types/UserProgress";
import axios from "axios";

export async function getUserProgressByChapterId(chapterId: string) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/user-progress/${chapterId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching course:", error);
    throw error;
  }
}

interface courseSidebarProps {
  course: Course & {
    chapters: (Chapter & {
      userProgress: UserProgress[] | null;
    })[];
  };
  progressCount: number;
}

export const CourseSidebar = async ({
  course,
  progressCount,
}: courseSidebarProps) => {
  const { userId } = auth();

  if (!userId) {
    return redirect("/");
  }

  const purchase = await db.purchase.findUnique({
    where: {
      userId_courseId: {
        userId,
        courseId: course.id,
      },
    },
  });

  return (
    <div className="h-full border-r flex flex-col overflow-y-auto shadow-sm">
      <div className="p-8 flex flex-col border-b">
        <h1 className="font-semibold">{course.title}</h1>
        {purchase && (
          <div className="mt-10">
            <CourseProgress variant="success" value={progressCount} />
          </div>
        )}
      </div>
      <div className="flex flex-col w-full">
        {course.chapters.map(async (chapter) => {
          const userProgress = await getUserProgressByChapterId(chapter.id);
          return (
            <CourseSidebarItem
              key={chapter.id}
              id={chapter.id}
              label={chapter.title}
              isCompleted={!!userProgress?.isCompleted}
              courseId={course.id}
              isLocked={!chapter.isFree && !purchase}
            />
          );
        })}
      </div>
    </div>
  );
};
