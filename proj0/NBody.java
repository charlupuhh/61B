public class NBody {
	public static double time = 0;

	public static double readRadius(String fileName){
		In file = new In(fileName);
		int numPlanets = file.readInt();
		double radius = file.readDouble();
		return radius;
	}

	public static Body[] readBodies(String fileName){
		In file = new In(fileName);
		Body[] bodyOfBodies = new Body[file.readInt()];
		double radius = file.readDouble();
		for (int i = 0; i < bodyOfBodies.length; i++){
			bodyOfBodies[i] = new Body(file.readDouble(), file.readDouble(), file.readDouble(), file.readDouble(), file.readDouble(), file.readString());
		}
		return bodyOfBodies;
	}

	//Draw the background then planets
	public static void drawGalaxy(Body[] b){
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (int i = 0; i < b.length; i++){
			b[i].draw();
		}
		StdDraw.show();
		StdDraw.pause(10);
	}

	//Update the X and Y Force arrays then use those to update the Body array.
	public static void updateShit(Body[] b, double[] xForce, double[] yForce){
		
		for (int i = 0; i < b.length; i++){
			xForce[i] = b[i].calcNetForceExertedByX(b);
			yForce[i] = b[i].calcNetForceExertedByY(b);
			b[i].update(time, xForce[i], yForce[i]);
		}
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Please enter command line arguments.");
		}
		// Collect Input
		double t = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String fileName = args[2];
		double radius = NBody.readRadius(fileName);
		Body[] bodies = readBodies(fileName);
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		drawGalaxy(bodies);
		double[] xForces = new double[bodies.length];
		double[] yForces = new double[bodies.length];

		//Until designated time, 
		while (time < t){
			updateShit(bodies, xForces, yForces);
			drawGalaxy(bodies);
			time += dt;
		}

		//Print final standings
		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
        		bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}
}