export interface Project {
  name: string;
  description: string;
  link?: string;
  skills?: string[];
}

export interface Experience {
  company: string;
  title: string;
  dateRange: string;
  bullets: string[];
}

export interface Education {
  school: string;
  degree: string;
  dateRange: string;
  achievements: string[];
}

export interface SiteConfig {
  name: string;
  title: string;
  description: string;
  accentColor: string;
  social: {
    email?: string;
    linkedin?: string;
    twitter?: string;
    github?: string;
  };
  aboutMe: string;
  skills: string[];
  projects: Project[];
  experience: Experience[];
  education: Education[];
}

export const siteConfig: SiteConfig = {
  name: "Emmanuel Akhigbe",
  title: "Full-Stack Software Developer",
  description: "Portfolio website of Emmanuel Akhigbe",
  accentColor: "#1d4ed8",
  social: {
    // Add these if you want them shown (links/icons only render when set):
    // email: "you@example.com",
    // linkedin: "https://linkedin.com/in/yourprofile",
    github: "https://github.com/Emms434",
  },
  aboutMe:
    "I'm a Computer Science student at Ontario Tech University who enjoys building full-stack applications end to end, from object-oriented domain models in Java to REST APIs, relational databases, React front ends and cloud deployment. I like turning small console programs into production-style systems with clean architecture, containerised local environments and automated CI/CD.",
  skills: [
    "Java",
    "Spring Boot",
    "JavaScript",
    "React",
    "Vite",
    "PostgreSQL",
    "Flyway",
    "Docker",
    "AWS",
    "GitHub Actions",
    "Git",
  ],
  projects: [
    {
      name: "Restaurant Ordering System",
      description:
        "Full-stack ordering app that grew from a Java console program into a Spring Boot REST API backed by PostgreSQL (Flyway migrations) and a React + Vite customer UI. It runs locally with Docker Compose and deploys to AWS through GitHub Actions: S3 + CloudFront for the frontend, Elastic Beanstalk for the API and RDS for the database.",
      link: "https://github.com/Emms434/Restaurant-Ordering-System",
      skills: ["Java", "Spring Boot", "PostgreSQL", "React", "Docker", "AWS"],
    },
    {
      name: "Campus Map",
      // TODO: replace with a one-to-two sentence summary of the project.
      description: "Campus map project.",
      link: "https://github.com/Emms434/Campus-Map",
    },
    {
      name: "Catalog Project",
      // TODO: replace with a summary of the project and your role on the team.
      description: "Collaborative catalog project.",
      link: "https://github.com/GameKhan13/Catalog-Project",
    },
  ],
  // Add work history here; the Experience section stays hidden while empty.
  experience: [],
  education: [
    {
      school: "Ontario Tech University",
      // TODO: confirm degree name and dates.
      degree: "Computer Science",
      dateRange: "Present",
      achievements: [],
    },
  ],
};
