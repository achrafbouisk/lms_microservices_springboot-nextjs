import { auth } from "@clerk/nextjs";
import { redirect } from "next/navigation";

import { DataTable } from "./_components/data-table";
import { columns } from "./_components/columns";
import axios from "axios";

export async function getCourses(userId: string) {
  try {
    const response = await axios.get(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}/api/courses/courses-by-user/${userId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching course:", error);
    throw error;
  }
}

const coursesPage = async () => {
  const { userId } = auth();

  if (!userId) {
    return redirect("/");
  }

  const courses = await getCourses(userId);

  return (
    <div className="p-6">
      <DataTable columns={columns} data={courses} />
    </div>
  );
};

export default coursesPage;
