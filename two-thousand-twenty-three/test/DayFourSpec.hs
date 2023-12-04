module DayFourSpec (main, spec) where

import Test.Hspec
import Test.QuickCheck
import Lib (readPuzzleInput)
import DayFour

main :: IO ()
main = hspec spec

spec :: Spec
spec = do
  describe "Day Four" $ do
    describe "points" $ do
      it "returns 8 for Card 1" $ do
        points (makeCard "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53") `shouldBe` 8
        
    it "solves the puzzle" $ do
      input <- readPuzzleInput 2023 4 "test/.cache/day_four.txt"
      sum (map (points . makeCard) input) `shouldBe` 25651 -- Part One
