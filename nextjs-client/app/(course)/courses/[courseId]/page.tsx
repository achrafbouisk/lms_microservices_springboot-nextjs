import { getCourse } from "@/actions/get-chapter";
import { redirect } from "next/navigation";

const courseIdPage = async ({ params }: { params: { courseId: string } }) => {
  const course = await getCourse(params.courseId);
  
  if (!course) {
    return redirect("/");
  }

  return redirect(`/courses/${course.id}/chapters/${course.chapters[0].id}`);
};

export default courseIdPage;
