module DayFour (makeCard, points) where

import Data.List (intersect)
import Data.List.Split (splitOn)

data Card = Card [Int] [Int] deriving (Show, Eq)

makeCard :: String -> Card
makeCard s =
  let [_, input] = splitOn ":" s
      [winningStr, myNumbersStr] = splitOn "|" input
      winningNumbers = map read $ words winningStr
      myNumbers = map read $ words myNumbersStr
  in Card winningNumbers myNumbers

-- | Calculate the points for a card
-- To calculate the points for a card, we need to find the intersection of the
-- winning numbers and the numbers on the card. If there is no intersection,
-- the card gets 0 points. If there is one intersection, the card gets 1 point.
-- If there are more than one intersections, the card gets 1 point for the
-- first intersection, and each additional intersection doubles the points.
points :: Card -> Int
points (Card a b ) =
  case a `intersect` b of
    [] -> 0
    [_] -> 1
    xs -> 2 ^ (length xs - 1)
