<?php
/**
 * OnTop Notifications API
 * Created by Poya R. (poya-r.com) on 2016-04-12.
 * Use this api to send notifications to yourself through OnTop Notification mobile app.
 * For more info visit:
 *      https://github.com/ontopapp/ontop-push
 *      https://ontop.tech
 */
class OnTop
{
    const VERSION_CODE = 1;
    const SEND_END_POINT = "https://ontop.tech/api/push";
    private $appId = "";
    private $appSecret = "";
    private $message = "";
    private $category = "";
    private $action = "";
    private $noti_action_url = "";
    private $custom = array();

    /**
     * OnTop constructor.
     */
    public function __construct($appId, $appSecret)
    {
        $this->appId = $appId;
        $this->appSecret = $appSecret;
    }

    /**
     * Message that will primarily be shown on the notification
     * Max 250 characters
     *
     * @param string $message   the text to show on the notification
     */
    public function setMessage($message)
    {
        $this->message = $message;
    }

    /**
     * (Optional)
     * Add a category to represent the entities/categories. (i.e. "user", "song")
     * Can contain only alphabet, underscore, and numbers with max 64 characters.
     *
     * @param string $category      Name of the category
     */
    public function setCategory($category)
    {
        $this->category = $category;
    }

    /**
     * Add a action tag to represent the action performed. (i.e. "like", "add", "share")
     * Can contain only alphabet, underscore, and numbers with max 64 characters.
     *
     * @param string $action        Name of the action
     */
    public function setAction($action)
    {
        $this->action = $action;
    }

    /**
     * You can add any external url to your notification.
     * This will show up under your notification as a clickable action that'll take you to the
     * supplied URL.
     *
     * @param string $url        Target URL
     */
    public function setNotificationAction($url)
    {
        $this->noti_action_url = $url;
    }

    /**
     * Add a custom field to hold any meta data for future reference.
     *
     * @param string $key       the key of the value being sent (i.e. "user_name")
     * @param string $value     the value being sent (i.e. "John Doe")
     */
    public function setCustom($key, $value)
    {
        $this->custom[$key] = $value;
    }

    /**
     * Returns the very base API call url with authentication info to be used for either GET/POST
     * You don't need this if you're using either {@link OnTop#getCompiledUrl()}
     * or {@link OnTop#send()}
     *
     * @return string Base url with required auth info
     */
    private function getBaseUrl()
    {
        $url = self::SEND_END_POINT . "?api_ver=" . self::VERSION_CODE;
        return $url;
    }

    /**
     * Returns a compiled GET call url based on current values. Note it'll issue a warning
     * to use POST if the URL is too long for a GET call
     * You dont need to use this if you're using {@link OnTop#send()}
     *
     * @return string Compiled URL
     */
    public function getCompiledUrl()
    {
        $url = $this->getBaseUrl();
        $url .= "&id=" . $this->appId;
        $url .= "&key=" . $this->appSecret;
        if ($this->category != "")          $url .= "&category=" . $this->category;
        if ($this->action != "")            $url .= "&action=" . $this->action;
        if ($this->message != "")           $url .= "&message=" . urlencode($this->message);
        if ($this->noti_action_url != "")   $url .= "&noti_action_url=" . urlencode($this->noti_action_url);
        if (!empty($this->custom))          $url .= "&custom=" . urlencode(json_encode($this->custom));
        // As a last resort if url is too long for a GET call
        // cut it short so that the call doesn't fail
        if(strlen($url) > 1000)
            $url = substr($url, 0, 1000);
        return $url;
    }

    /**
     * Sends the notification through a POST call.
     * If you need to make a GET call use {@link OnTop#getCompiledUrl()}
     * If you want to build you're own POST call use {@link OnTop#getBaseUrl()}
     * to get the base url required for either GET/POST
     *
     * This function uses curl-less method so need for curl plugin
     */
    public function send()
    {
        $url = $this->getBaseUrl();
        $data = array();

        $data['id']  = $this->appId;
        $data['key'] = $this->appSecret;
        if ($this->message != "")           $data['message'] = $this->message;
        if ($this->category != "")          $data['category'] = $this->category;
        if ($this->action != "")            $data['action'] = $this->action;
        if ($this->noti_action_url != "")   $data['noti_action_url'] = $this->noti_action_url;
        if (!empty($this->custom))          $data['custom'] = json_encode($this->custom);
        // use key 'http' even if you send the request to https://...
        $options = array(
            'http' => array(
                'header'  => "Content-type: application/x-www-form-urlencoded",
                'method'  => 'POST',
                'content' => http_build_query($data)
            )
        );
        $context  = stream_context_create($options);
        $result = file_get_contents($url, false, $context);
        if ($result === FALSE) {
            /* Ignore any errors or log them silently */
        }
    }
}
