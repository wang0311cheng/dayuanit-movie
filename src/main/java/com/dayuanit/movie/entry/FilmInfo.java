package com.dayuanit.movie.entry;

public class FilmInfo {
    private String filmId;

    private String actorName;

    private Integer actorType;

    private String actorPic;

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId == null ? null : filmId.trim();
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName == null ? null : actorName.trim();
    }

    public Integer getActorType() {
        return actorType;
    }

    public void setActorType(Integer actorType) {
        this.actorType = actorType;
    }

    public String getActorPic() {
        return actorPic;
    }

    public void setActorPic(String actorPic) {
        this.actorPic = actorPic == null ? null : actorPic.trim();
    }
}