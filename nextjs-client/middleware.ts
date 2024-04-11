import { authMiddleware } from "@clerk/nextjs";

// This example protects all routes including api/trpc routes
// Please edit this to allow other routes to be public as needed.
// See https://clerk.com/docs/references/nextjs/auth-middleware for more information about configuring your middleware
export default authMiddleware({
  publicRoutes: [
    "/api/user",
    "/api/webhook",
    "/api/mux-data/update",
    "/api/uploadthing",
    "/api/mux-data/get",
    "/api/mux-data/delete",
    "/api/mux-data/course",
  ],
});

export const config = {
  matcher: ["/((?!.*\\..*|_next).*)", "/", "/(api|trpc)(.*)"],
};
