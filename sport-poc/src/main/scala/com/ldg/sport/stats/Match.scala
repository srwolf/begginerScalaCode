package com.ldg.sport.stats

import com.ldg.sport.stats.Teams.{AwayTeam, HomeTeam}

//Model the match
case class Match (homeTeam: HomeTeam, awayTeam: AwayTeam, date: String, season : Int){
  // construction reference =>
  // this.result = Integer.toString(homeTeam.getGoals())+"-"+Integer.toString(awayTeam.getGoals());
  def result: Int = homeTeam.goals + awayTeam.goals
}

