public class Planet {
	// First constructor that initialize the six instance variables
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private double G = 6.67 * Math.pow(10,-11);
	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}
	// Second constructor that
	public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;

	}
	// This method calculates the distance between two Planets.
	// This method takes in a single Planet and should return a double object
	public double calcDistance(Planet p) {
		double distance;
		double dx = this.xxPos - p.xxPos;
		double dy = this.yyPos - p.yyPos;
		if (this.equals(p)){
			distance = 0;
		} else {
			distance = Math.sqrt(dx * dx + dy * dy);
		}
		return distance;
	}
	// This method takes in a planet and returns a double object
	// The force exerted on the given planet by the input Planet
	public double calcForceExertedBy(Planet p) {
		double force;
		if (this.equals(p)) {
			force = 0;
		} else {
			force = G * this.mass * p.mass / (this.calcDistance(p) * this.calcDistance(p));

		}
		return force;
	}
	// These two methods calcalute the force exterted in x and y directions
	public double calcForceExertedByX(Planet p) {
		double forceX;
		double dx = p.xxPos - this.xxPos;
		if (this.equals(p)){
			forceX = 0;
		} else {
			forceX = this.calcForceExertedBy(p) * dx / this.calcDistance(p);
		}
		return forceX;
	}
	public double calcForceExertedByY(Planet p) {
		double forceY;
		double dy = p.yyPos - this.yyPos;
		if (this.equals(p)) {
			forceY = 0;
		} else {
			forceY = this.calcForceExertedBy(p) * dy / this.calcDistance(p);
		}
		return forceY;
	}
	// These two methods each take in an array of Planets and calculatethe net X and net Y force
	public double calcNetForceExertedByX(Planet[] p) {
		double forceX = 0;
		int index = p.length - 1;
		while (index >= 0) {
			forceX += this.calcForceExertedByX(p[index]);
			index -= 1;
		}
		return forceX;
	}
	public double calcNetForceExertedByY(Planet[] p) {
		double forceY = 0;
		int index = p.length - 1;
		while (index >= 0) {
			forceY += this.calcForceExertedByY(p[index]);
			index -= 1;
		}
		return forceY;
	}
	// This method updates the Planet's location
	public void update(double dt, double fX, double fY) {
		double accX = fX / this.mass;
		double accY = fY / this.mass;
		this.xxVel += dt * accX;
		this.yyVel += dt * accY;
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}
	// This method uses StdDraw API to draw the Planet's img at the Planet's posotion
	public void draw() {
		String path = "images/" + this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, path);
	}
}
