using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text;
using System.IO;
using System.Net;
using Newtonsoft.Json;

namespace ParkingManagement
{
    //This holds all the functions that the pages need to share

    public static class Shared
    {
        static string serverAddress = @"http://ec2-54-200-98-161.us-west-2.compute.amazonaws.com:8080";


        //AT THE MOMENT, WE ARE ONLY GETTING 1 LOT FROM THE DATABASE and this causes problems, so I changed this
        //to act like it is only trying to get 1 lot (it asks for the list of all of them).
        //My substring'ing of the incoming string may have ether helped fix it or break it. 
       // public static ParkingLot getParkingLots()
        public static AllLots getParkingLots()
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/lots") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;
            req.Headers.Add("Authorization", "3addbbc3d6a464eba3f57993411144158b0d312c");

            string result;
            AllLots resultObject;
           // AllLots resultObject;
            using (HttpWebResponse resp = req.GetResponse() as HttpWebResponse)
            {
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                result = reader.ReadToEnd();
                result.Trim();
             //   string stringResult = result.ToString();
                string stringResult = result.Substring(1, result.Length - 2);   //We are geting square brackets that make things complicated.
                                                                                //When the database has more than 1 lot, it should fix itself.

                resultObject = JsonConvert.DeserializeObject<AllLots>(stringResult);
               // resultObject = JsonConvert.DeserializeObject<AllLots>(stringResult);
            }
            return resultObject;
        }

        /*Get the names of the parking lots. This is used to populate the drop-down list.
         * For convenience, the parameter is optional. Including the parameter (or using a global)
         * will allow the function to skip the slow call to the server.
         */
        public static string[] getLotNames()   //public static string[] getLotNames(AllLots inLots)
        {
            AllLots inLots = getParkingLots();
            string[] names = new string[2];  //just to compile
            names[0] = "testing1";  //just to compile
            names[1] = "testing2";  //just to compile

            /*
            AllLots inLots = getParkingLots();
            if (inLots == null)
            {
                inLots = getParkingLots();
            }
            */
            /*
            string[] names = new string[inLots.lotList.Length];
            for (int i = 0; i < inLots.lotList.Length; i++)
            {
                names[i] = "Lot " + inLots.lotList[i].lotNumber;
            }
            */
            return names;
        }

    }


    //JSON OBJECTS
    public class Point
    {
        public float lat { get; set; }
        public float lng { get; set; }
    }
    public class ParkingLot
    {
        public string lotNumber { get; set; }
        public int type { get; set; }
        public int active { get; set; }
        public Point[] points { get; set; }
    }
    public class AllLots
    {
        public ParkingLot[] lotList { get; set; }
    }

}