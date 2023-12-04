{-# LANGUAGE OverloadedStrings #-}

module Lib
    ( readPuzzleInput
    ) where

import System.Directory (doesFileExist, getFileSize)
import System.Environment (lookupEnv)
import Data.ByteString.Char8 (pack)
import qualified Data.ByteString as BS
import Network.HTTP.Simple (parseRequest, addRequestHeader, getResponseBody, httpBS)

checkFileExistsWithData :: FilePath -> IO Bool
checkFileExistsWithData path = do
  exists <- doesFileExist path
  if not exists
    then return False
    else do
      size <- getFileSize path
      return $ size > 0

cacheInput :: Int -> Int -> FilePath -> IO ()
cacheInput year day path = do
  isCached <- checkFileExistsWithData path
  token' <- lookupEnv "AOC_TOKEN"
  case (isCached, token') of
    (True, _) -> putStrLn "Using cached input"
    (False, Nothing) -> putStrLn "No cached input found and no AOC_TOKEN set"
    (False, Just token) -> do
      let url = "https://adventofcode.com/" <> show year <> "/day/" <> show day <> "/input"
      req <- parseRequest url
      let req' = addRequestHeader "cookie" (pack token) req
      body <- getResponseBody <$> httpBS req'
      BS.writeFile path body

readPuzzleInput :: Int -> Int -> FilePath -> IO [String]
readPuzzleInput year day path = do
  cacheInput year day path
  lines <$> readFile path
