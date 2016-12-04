package com.ldg.sport.stats

/**
  *
  * formartting info
  *
  * Replacing name "'" X "''"
  *
  * Replacing location "'" X "''"
  *
  * Replacing goal String X Int
  *
  **/


object Teams {

case class AwayTeam(name:String,goals:Int,location:String)
case class HomeTeam(name:String,goals:Int,location:String)
case class TeamPerSeason(season:String,teamName:String,url:String)

}