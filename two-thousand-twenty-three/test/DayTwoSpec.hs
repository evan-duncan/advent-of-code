module DayTwoSpec (main, spec) where

import Test.Hspec
import Test.QuickCheck
import Lib (readPuzzleInput)
import DayTwo

main :: IO ()
main = hspec spec

spec :: Spec
spec = do
  describe "Day Two" $ do
    describe "parseGame" $ do
      it "should return asdf for \"Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green\"" $ do
        parseGame "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green" `shouldBe` Right [[(Blue, 3), (Red, 4)], [(Red, 1), (Green, 2), (Blue, 6)], [(Green, 2)]]
    it "solves part 1" $ do
      input <- readPuzzleInput 2023 2 "test/.cache/day_two.txt"
      let rules = [(Red, 12), (Green, 13), (Blue, 14)]
          games = map parseGame input
          indices = map (+1) $ indicesOfPassingGames rules $ [result | Right result <- games]
          
      sum indices `shouldBe` 6 -- Part One
