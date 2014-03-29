using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text;
using System.IO;
using System.Net;
using Newtonsoft.Json;

namespace ParkingManagement.WebContent.UsersManagement
{
    public static class ManageUsers
    {
        static string serverAddress = @"http://ec2-54-200-98-161.us-west-2.compute.amazonaws.com:8080";
        static string API_KEY;

        public class Login
        {
            public string username { get; set; }
            public string password { get; set; }

            public Login( string username, string password)
            {
                this.username  = username;
                this.password = password;
             }
        }

        public class Key
        {
            public string key { get; set; }
          
            public Key( string key)
            {
                this.key = key;
              
             }
        }

        public class User
        {
            public string UserName { get; set; }
            public string Password { get; set; }
            public int RoleId { get; set; }
            public string firstName { get; set; }
            public string lastName { get; set; }
            public string email { get; set; }
           
            public User(){}

                     /// <summary>
            /// User constructor to create new user
            /// </summary>
            /// <param name="userName"></param>
            /// <param name="password"></param>
            /// <param name="roleId"></param>
            /// <param name="firstName"></param>
            /// <param name="lastName"></param>
            /// <param name="email"></param>
            public User( string userName, string password, int roleId, string firstName, string lastName, string email)
            {
                this.UserName = userName;
                this.Password = password;
                this.RoleId = roleId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;

            }

            
        };

        public class editUser
        {
            public int userId { get; set; }
            public string userName { get; set; }
            public int roleId { get; set; }
            public string firstName { get; set; }
            public string lastName { get; set; }
            public string email { get; set; }
            public int isAdmin { get; set; }
            public string Password { get; set; }
   
            public editUser(int userId, string userName, int roleId, string firstName, string lastName, string email, int isAdmin, string Password)
            {
                this.userId = userId;
                this.userName = userName;
                this.roleId = roleId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.isAdmin = isAdmin;
                this.Password = Password;
            }

         };

        public class SuperUser:User
        {
            public int UserId { get; set; }
            public string Role { get; set; }
            public string Admin { get; set; }

            public SuperUser() { }
            public SuperUser(int UserId, string userName, int roleId, string firstName, string lastName, string email)
            {
                this.UserId = UserId;
                this.UserName = userName;
               this.RoleId = roleId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
            }
        }


        public class UserRoles
        {
              public int RoleId { get; set; }
              public string Description { get; set; }
        }

        /// <summary>
        /// Return all users from the database
        /// </summary>
        /// <returns></returns>
        public static List<SuperUser> getUsers()
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/users") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;

            //TODO: api key should be replaced by the session info when the user log in
            req.Headers.Add("Authorization", API_KEY);

            string result;
            List<SuperUser> allUsers;

            using (HttpWebResponse resp = req.GetResponse() as HttpWebResponse)
            {
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                result = reader.ReadToEnd();
                result.Trim();
                allUsers = (List<SuperUser>)JsonConvert.DeserializeObject(result, typeof(List<SuperUser>));

            }
            return allUsers;
        }

        /// <summary>
        /// Return all roles from the database
        /// </summary>
        /// <returns></returns>
        public static List<UserRoles > getUserRoles()
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/roles") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;

            //TODO: api key should be replaced by the session info when the user log in
            req.Headers.Add("Authorization",API_KEY );

            string result;
            List<UserRoles> allRoles;

            using (HttpWebResponse resp = req.GetResponse() as HttpWebResponse)
            {
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                result = reader.ReadToEnd();
                result.Trim();
                allRoles = (List<UserRoles>)JsonConvert.DeserializeObject(result, typeof(List<UserRoles>));

            }
            return allRoles;
        }

        /// <summary>
        /// Add new user to the database
        /// </summary>
        /// <param name="newUser"></param>
        public static void addUser(User newUser)
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/users") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Post;

            //TODO: api key should be replaced by the session info when the user log in
            req.Headers.Add("Authorization", API_KEY);

            using (var streamWriter = new StreamWriter(req.GetRequestStream()))
            {

                string json = JsonConvert.SerializeObject(newUser);
                streamWriter.Write(json);
                streamWriter.Flush();
                streamWriter.Close();
            }

            HttpWebResponse httpResponse = (HttpWebResponse)req.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
            }
        }


        /// <summary>
        /// Edit user to the database
        /// </summary>
        /// <param name="newUser"></param>
        public static void updateUser(editUser newUser)
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/users/" + newUser.userId) as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Post;

            //TODO: api key should be replaced by the session info when the user log in
            req.Headers.Add("Authorization", API_KEY);

            using (var streamWriter = new StreamWriter(req.GetRequestStream()))
            {

                string json = JsonConvert.SerializeObject(newUser);
                streamWriter.Write(json);
                streamWriter.Flush();
                streamWriter.Close();
            }

            HttpWebResponse httpResponse = (HttpWebResponse)req.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
            }
        }

        /// <summary>
        ///Delete user
        /// </summary>
        /// <param name="userId"></param>
        public static void deleteUser( int userId )
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/users/" + userId.ToString()) as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = "DELETE";

            req.Headers.Add("Authorization", API_KEY);
            HttpWebResponse httpResponse = (HttpWebResponse)req.GetResponse();
            
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
            }

        }

        /// <summary>
        /// Login to the database through API
        /// </summary>
        /// <param name="userName"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        public static bool login(Login newLogin)
        {
            try
            {
                HttpWebRequest req = WebRequest.Create(serverAddress + "/login") as HttpWebRequest;
                req.ContentType = "application/json";
                req.Method = WebRequestMethods.Http.Post;
           
                using (var streamWriter = new StreamWriter(req.GetRequestStream()))
                {
                    string json = JsonConvert.SerializeObject(newLogin);
                    streamWriter.Write(json);
                    streamWriter.Flush();
                    streamWriter.Close();
                }

                HttpWebResponse httpResponse = (HttpWebResponse)req.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    string result = streamReader.ReadToEnd();
                    Key newKey = (Key)JsonConvert.DeserializeObject(result, typeof(Key));
                    if (newKey.key.Length > 32)
                    {
                        API_KEY = newKey.key;
                        return true;
                    }
               
                    return false ;
                }
             }
            catch
            {
                return false;
            }
            
        }

    }
}