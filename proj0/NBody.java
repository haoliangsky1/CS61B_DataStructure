public class NBody {
	// This method returns radius of the universe in that file
	public static double readRadius(String args) {
		// Start reading in the data file
		In in = new In(args);
		// Read the second integer of the file
		// double radius = 0;
		// int counter = 1;
		// while(!in.isEmpty()) {
		// 	if(counter == 3) {
		// 		break;
		// 	} else {
		// 		radius = in.readDouble();
		// 		counter += 1;
		// 	}
		// }
		in.readDouble();
		return in.readDouble();
	}
	// This method returns an array of Planets
	public static Planet[] readPlanets(String args) {
		In in = new In(args);
		int num = in.readInt();
		Planet[] planets = new Planet[num];
		in.readDouble();
		int counter = 0;
		while(counter < num){
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			planets[counter] = new Planet(xP, yP, xV, yV, m, img);
			counter += 1;
		}
		return planets;
	}
	public static void main(String[] args) {
		// Collecting all needed input
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);

		//Drawing the background
		// Set the scale in order to mathc the radius.
		StdDraw.setScale(-radius, radius);
		// Draw the image
		String imageToDraw = "images/starfield.jpg";
		StdDraw.picture(0, 0, imageToDraw);

		// Draw all of the Planets
		int count = 0;
		while(count < planets.length) {
			planets[count].draw();
			count += 1;
		}StdDraw.picture(0, 0, imageToDraw);
		// Create an animation
		double time = 0;
		while(time <= T) {
			// Create X, Y force array
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			// Calculate the net x and y forces for each planet
			int index = 0;
			while(index < planets.length) {
				xForces[index] = planets[index].calcNetForceExertedByX(planets);
				yForces[index] = planets[index].calcNetForceExertedByY(planets);
				index++;
			}
			// Update on each of the planets
			index = 0;
			while(index < planets.length){
				planets[index].update(dt, xForces[index], yForces[index]);
				index++;
			}
			// Draw background image
		    StdDraw.setScale(-radius, radius);
		    StdDraw.picture(0, 0, imageToDraw);
			// Draw all of the planets
			count = 0;
			while(count < planets.length) {
				planets[count].draw();
				count += 1;
			}
			// Pause the animation
			StdDraw.show(10);
			time += dt;
		}
		// When the simulation is over, print out the final state of the universe
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
				planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
		}		

	}

}
