package model;

public class Association {
    private Teams teams;
    private Season season;

    public Association() {
        this.teams = new Teams();
        this.season = new Season();
    }

    // Returns the list of teams
    public Teams getTeams() {
        return this.teams;
    }

    // Returns the current season
    public Season getSeason() {
        this.season.currentInit(this.teams.currentTeams());
        return this.season;

    }
}