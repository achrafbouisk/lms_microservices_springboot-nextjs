import { NavbarRoutes } from "@/components/navbar-routes";

import { CourseMobileSidebar } from "./course-mobile-sidebar";
import { Course } from "@/types/Course";
import { Chapter } from "@/types/Chapter";
import { UserProgress } from "@/types/UserProgress";

interface courseNavbarProps {
  course: Course & {
    chapters: (Chapter & {
      userProgress: UserProgress[] | null;
    })[];
  };
  progressCount: number;
};

export const CourseNavbar = ({
  course,
  progressCount,
}: courseNavbarProps) => {
  return (
    <div className="p-4 border-b h-full flex items-center bg-white shadow-sm">
      <CourseMobileSidebar
        course={course}
        progressCount={progressCount}
      />
      <NavbarRoutes />      
    </div>
  )
}