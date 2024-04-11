import { getCategories } from "@/app/(dashboard)/(routes)/search/page";
import { CourseCard } from "@/components/course-card";
import { Category } from "@/types/Category";
import { Course } from "@/types/Course";

type courseWithProgressWithCategory = Course & {
  category: Category | null;
  chapters: { id: string; isPublished: boolean }[]; // Add isPublished property to each chapter
  progress: number | null;
};

interface coursesListProps {
  items: courseWithProgressWithCategory[];
}

export const CoursesList = async ({ items }: coursesListProps) => {
  const categories = await getCategories();

  return (
    <div>
      <div className="grid sm:grid-cols-2 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-4 gap-4">
        {items.map((item) => {
          // Count the number of published chapters
          const publishedChaptersLength =
            item?.chapters?.filter((chapter) => chapter.isPublished)?.length ||
            0;
          return (
            <CourseCard
              key={item.id}
              id={item.id}
              title={item.title}
              imageUrl={item.imageUrl!}
              chaptersLength={publishedChaptersLength} // Pass the number of published chapters
              price={item.price!}
              progress={item.progress}
              category={categories.map((category: any) => {
                if (item?.category === category.id) {
                  return category.name;
                }
                return null; // Ensure you return a value for each iteration
              })}
            />
          );
        })}
      </div>
      {items?.length === 0 && (
        <div className="text-center text-sm text-muted-foreground mt-10">
          No courses found
        </div>
      )}
    </div>
  );
};
